package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.GENERAL_ERROR;
import static com.softserve.edu.util.Constants.ROLE_MANAGER;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GenericDao;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.dao.RoleRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.UserManager;
import com.softserve.edu.util.FieldForSearchController;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    @Qualifier("genericDaoImpl")
    private GenericDao<User> genericDao;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public String userHome(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            User user = userManager.findByUsername(auth.getName());

            List<Group> groups = groupRepository.findByUsers_Id(user.getId());
            List<Achievement> achievements = achievementRepository.findByUserId(user.getId());

            List<Group> openedGroups = groupRepository.findOpenedByUserId(user.getId());
            List<Competence> wantToAttend = competenceRepository.findByUsers_Id(user.getId());
            List<Competence> exceptOfList = new ArrayList<>();

            for (Group group : openedGroups) {
                exceptOfList.add(group.getCompetence());
            }
            exceptOfList.addAll(wantToAttend);

            List<Competence> competences = competenceManager.findExcept(exceptOfList);

            model.addAttribute("groups", groups);
            model.addAttribute("achievements", achievements);
            model.addAttribute("competences", competences);
            model.addAttribute("waiting_attend", wantToAttend);

            return "userHome";
        } catch (Exception e) {
            logger.error("Home user page error ", e);
            return GENERAL_ERROR;
        }
    }

    @PostMapping(value = "/userHome")
    public String attend(
            @RequestParam(value = "competence") long competenceId,
            Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            userManager.appendCompetence(userManager.findByUsername(auth.getName()).getId(),
                    competenceId);

            return "redirect:/user/userHome";
        } catch (UserManagerException e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }

    }

    @RequestMapping(value = "/admin/removeManager", method = RequestMethod.POST)
    public String removeManagerFromList(
            Model model,
            @RequestParam(value = "userlist", required = false, defaultValue = "") Long userId) {
        try {
            userManager.deleteById(userId);

            return "redirect:/admin/removeManager?status=success";
        } catch (UserManagerException e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/addManager", method = RequestMethod.GET)
    public String addManager(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        model.addAttribute("status", status);
        return "addManager";
    }

    @RequestMapping(value = "/admin/addManager", method = RequestMethod.POST)
    public
    @ResponseBody
    String addManager(@Valid User user, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "redirect:/admin/allManagers?status=error";
            }
            user.setRole(roleRepository.findByName(ROLE_MANAGER));
            userManager.createUser(user);
            return "admin/allManagers";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/allManagers", method = RequestMethod.GET)
    public String allManagers(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        try {
            List<User> managers = userRepository.findByRoleName(ROLE_MANAGER);
            Map<String, String> searchBy = new FieldForSearchController<>(
                    User.class).findAnnotation();
            model.addAttribute("total", managers.size());
            model.addAttribute("user", new User());
            model.addAttribute("searchBy", searchBy);
            model.addAttribute("userlist", managers);
            model.addAttribute("status", status);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
        return "allManagers";
    }

    @RequestMapping(value = "/admin/managers/search/{pattern}", method = RequestMethod.GET)
    public String allManagersDynamicSearch(
            @RequestParam(value = "parameterGet") String parameter,
            @RequestParam(value = "volume") int max,
            @RequestParam(value = "pagination") int paging,
            @RequestParam(value = "isFirstChar") boolean isFirstChar,
            @PathVariable(value = "pattern") String pattern,
            Model model) {
        try {
            int start = max * (paging - 1);

            List<User> dynamicUsers = genericDao.dynamicSearch(start,
                    max, parameter, pattern, isFirstChar, User.class);
//            Iterable<User> dynamicUsers = userRepository.findAll(predicate);

/*            List<User> allUsers = genericDao.dynamicSearch(0,
                    userRepository.findByRoleName(ROLE_MANAGER).size(), parameter, pattern,
                    isFirstChar, User.class);*/

            model.addAttribute("userlist", dynamicUsers);
//            model.addAttribute("currentSize", allUsers.size());

            return "dynamicSearch";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/removeManager/{id}", method = RequestMethod.GET)
    public String removeManagerById(Model model,
            @PathVariable(value = "id") Long userId) {
        try {
            userManager.deleteById(userId);
            return "redirect:/admin/allManagers?status=success";
        } catch (UserManagerException e) {
            return "errorPage";
        }
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    String getImage(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            model.addAttribute("username", auth.getName());

            return "image";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    String uploadFileHandler(@RequestParam("file") MultipartFile file,
            Model model) {

        try {
            if (!file.isEmpty() || file.getContentType().startsWith("image/")) {
                Authentication auth = SecurityContextHolder.getContext()
                        .getAuthentication();
                byte[] imageInByte = file.getBytes();
                User user = userManager.findByUsername(auth.getName());
                user.setPicture(imageInByte);
                userRepository.save(user);
            }
            return "redirect:/image";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }

    }

    @RequestMapping(value = "/showImage/{username}")
    public ResponseEntity<byte[]> showImage(
            @PathVariable(value = "username") String username) {
        try {
            User user = userManager.findByUsername(username);
            byte[] image;
            if (user != null && user.getPicture() != null) {
                image = user.getPicture();
            } else {
                URL url = Thread.currentThread().getContextClassLoader()
                        .getResource("defaultPicture.png");
                File file = new File(Objects.requireNonNull(url).getPath().replaceAll("%20", " "));
                image = Files.readAllBytes(file.toPath());
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/userprofile", method = RequestMethod.GET)
    public String userProfile(Model model, Principal principal) {
        try {
            User user = userManager.findByUsername(principal.getName());
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("username", user.getUsername());

            return "mainProfile";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.GET)
    public String editProfile(

            Model model, Principal principal) {
        try {
            User user = userManager.findByUsername(principal.getName());
            model.addAttribute("id", user.getId());
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("username", user.getUsername());

            return "userProfile";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.POST)
    public String editProfileUpdate(@Valid User user, BindingResult result,
            Model model, Principal principal) {
        User currentUser = userManager.findByUsername(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("username", currentUser.getUsername());

        if (result.hasErrors()) {
            model.addAttribute("error", "fields incorrectly filled out.");
            return "userProfile";
        }
        try {
            userManager.updateUser(currentUser.getId(), user.getName(),
                    user.getSurname(), null, null, user.getEmail(), null);
            return "mainProfile";
        } catch (UserManagerException e) {
            model.addAttribute("error", "email already exists.");
            logger.error(e.getMessage());
            return "userProfile";
        }
    }

    @RequestMapping(value = "/passwordchanging", method = RequestMethod.GET)
    public String passwordChange() {
        return "passwordchanging";
    }

    @RequestMapping(value = "/passwordchanging", method = RequestMethod.POST)
    public String passwordChanger(
            @RequestParam(value = "oldPassword", required = false, defaultValue = "") String oldPassword,
            @RequestParam(value = "newPassword", required = false, defaultValue = "") String newPassword,
            @RequestParam(value = "confirmPassword", required = false, defaultValue = "") String confirmPassword,
            Principal principal, Model model) {

        try {
            User user = userManager.findByUsername(principal.getName());

            if (encoder.matches(oldPassword, user.getPassword())
                    && newPassword.length() >= 4) {
                userManager.updateUser(user.getId(), null, null, null, newPassword,
                        null, null);
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("surname", user.getSurname());
                model.addAttribute("username", user.getUsername());
                return "mainProfile";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("error", true);
        }
        return "passwordchanging";

    }
}

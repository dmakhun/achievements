package com.softserve.edu.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.impl.CompetenceManagerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CompetenceManagerTest {

    @InjectMocks
    private CompetenceManager competenceManager = new CompetenceManagerImpl();
    @Mock
    private AchievementTypeRepository achievementTypeRepository;
    private int idMock = 1;
    private long idMockLong = 1;
    private Group aGroup;
    private AchievementType achievementType;
    private List<Group> listGroups = new ArrayList<>(idMock);
    private List<Competence> listCompetences = new ArrayList<>();
    private List<AchievementType> listAchievementTypes = new ArrayList<>();
    private Set<Competence> competences = new HashSet<>();
    private Competence competence;
    private java.lang.Class competenceClass;

    @BeforeEach
    public void setUp() {
        achievementType = new AchievementType();
        aGroup = new Group();
        competence = new Competence();
        listGroups.add(aGroup);
        listCompetences.add(competence);
        listAchievementTypes.add(achievementType);
        competences.add(competence);
        competenceClass = Competence.class;
    }

    @Test
    public void testGetTypesOfAchievements() {
        when(achievementTypeRepository.findByCompetenceId(idMockLong))
                .thenReturn(listAchievementTypes);
        List<AchievementType> listActual = competenceManager
                .findAchievementsTypesByCompetenceId(idMockLong);
        assertEquals(listAchievementTypes, listActual);
    }

    @Test
    public void testCreateEquals() throws CompetenceManagerException {
        Competence competenceExpected = new Competence();
        competenceExpected.setName("Java");
        competence = competenceManager.create("Java");
        assertEquals(competenceExpected, competence);
    }

    @Test
    public void testCreateNotSame() throws CompetenceManagerException {
        Competence competenceExpected = new Competence();
        competenceExpected.setName("Android");
        competence = competenceManager.create("Java");
        assertNotSame(competenceExpected, competence);
    }

    @Test
    public void testDeleteByStringFalse() throws CompetenceManagerException {
        Assertions.assertThrows(CompetenceManagerException.class,
                () -> competenceManager.delete(idMockLong));
    }

}

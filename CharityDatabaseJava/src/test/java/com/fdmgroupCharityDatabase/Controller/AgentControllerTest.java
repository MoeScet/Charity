package com.fdmgroupCharityDatabase.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;


import com.fdmgroupCharityDatabase.Service.AgentService;


import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class AgentControllerTest {
    

    @Mock
    private AgentService agentService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private ModelAndView modelAndView;

    AgentController agentController = new AgentController();
    
    @BeforeEach
    public void setUp() {
    }
    
	@Test
	public void testGoToIndexPage() {
	    String result = agentController.goToIndexPage();
	    assertEquals("index", result);
	}

    @Test
    public void testGoToLoginPage() {
        String expectedViewName = "login";
        String actualViewName = agentController.goToLoginPage();
        assertEquals(expectedViewName, actualViewName);
    }
    
    @Test
    public void testLogout() {
        String result = agentController.logout(httpSession);

        verify(httpSession).invalidate();
        
        assertEquals("redirect:/", result);
    }
    
    @Test
    public void testGoToHomePageWithNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(null);

        // Act
        String result = agentController.goToHomePage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("redirect:/login", result);
    }
    
    @Test
    void testGoToHomePageWithNonNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(new Object());

        // Act
        String result = agentController.goToHomePage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("home", result);
    }
    
    @Test
    void testGoToDonorPageWithNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(null);

        // Act
        String result = agentController.goToDonorPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("redirect:/login", result);
    }
    
    @Test
    void testGoToDonorPageWithNonNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(new Object());

        // Act
        String result = agentController.goToDonorPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("donor", result);
    }
    
    @Test
    void testGoToBeneficiaryPageWithNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(null);

        // Act
        String result = agentController.goToBeneficiaryPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("redirect:/login", result);
    }
    
    @Test
    void testGoToBeneficiaryPageWithNonNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(new Object());

        // Act
        String result = agentController.goToBeneficiaryPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("beneficiary", result);
    }
    
    @Test
    void testGoToDonationPageWithNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(null);

        // Act
        String result = agentController.goToDonationPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("redirect:/login", result);
    }
    
    @Test
    void testGoToDonationPageWithNonNullCurrentUser() {
        // Arrange
        Mockito.when(httpSession.getAttribute("current_user")).thenReturn(new Object());

        // Act
        String result = agentController.goToDonationPage(httpSession);

        // Assert
        Mockito.verify(httpSession, Mockito.times(1)).getAttribute("current_user");
        Assertions.assertEquals("donation", result);
    }
    
    
        

    
 
    
	






//
//
//	@Test
//	void testVerifyUser() {
//		fail("Not yet implemented");
//	}
//

//

//
//	@Test
//	void testGoToBeneficiaryPage() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGoToTransactionPage() {
//		fail("Not yet implemented");
//	}

}

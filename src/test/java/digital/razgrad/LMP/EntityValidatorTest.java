package digital.razgrad.LMP;

import digital.razgrad.LMP.hellper.EntityValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntityValidatorTest {
    @InjectMocks
    EntityValidator entityValidator;
    @Test
    void testCheckSaveSuccessWithCorrectInput() {
        //GIVEN
        Object testObject = new Object();
        //WHEN
        String result = entityValidator.checkSaveSuccess(testObject);
        //THEN
        Assertions.assertEquals("Успешен запис в базата данни!", result);
    }
    @Test
    void testCheckSaveSuccessWithIncorrectInput() {
        //GIVEN
        Object testObject = null;
        //WHEN
        String result = entityValidator.checkSaveSuccess(testObject);
        //THEN
        Assertions.assertEquals("Неуспешен запис в базата данни! :(", result);
    }
    @Test
    void testCheckDeleteSuccessWithCorrectInput() {
        //GIVEN
        boolean testFlag = false;
        //WHEN
        String result = entityValidator.checkDeleteSuccess(testFlag);
        //THEN
        Assertions.assertEquals("Успешено изтриване от базата данни!", result);
    }
    @Test
    void testCheckDeleteSuccessWithIncorrectInput() {
        //GIVEN
        boolean testFlag = true;
        //WHEN
        String result = entityValidator.checkDeleteSuccess(testFlag);
        //THEN
        Assertions.assertEquals("Неуспешено изтриване от базата данни!", result);
    }
}

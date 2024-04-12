package digital.razgrad.LMP.hellper;

import org.springframework.stereotype.Service;


@Service
public class EntityValidator {
    public String checkSaveSuccess(Object object) {
        if (object != null) {
            return "Успешен запис в базата данни!";
        } else {
            return "Неуспешен запис в базата данни! :(";
        }
    }
    public String checkDeleteSuccess(boolean flag) {
        if (!flag) {
            return "Успешено изтриване от базата данни!";
        } else {
            return "Неуспешено изтриване от базата данни!";
        }
    }
}

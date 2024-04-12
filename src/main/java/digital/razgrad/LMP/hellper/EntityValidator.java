package digital.razgrad.LMP.hellper;

import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

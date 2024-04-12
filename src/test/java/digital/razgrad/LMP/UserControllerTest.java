package digital.razgrad.LMP;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUserLogin() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"usernameOrEmail\" name=\"usernameOrEmail\" placeholder=\"Потребителско име или e-mail\">")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Парола\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Вход</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../'\">Отказ</button>")));
    }

    @Test
    void testUserRegistration() throws Exception {
        this.mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"username\" placeholder=\"Потребителско име\" name=\"username\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"password\" placeholder=\"Парола\" name=\"password\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"repeatPassword\" placeholder=\"Повтори паролата\" name=\"repeatPassword\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"firstName\" placeholder=\"Име\" name=\"firstName\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"lastName\" placeholder=\"Фамилия\" name=\"lastName\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"email\" class=\"form-control\" id=\"email\" placeholder=\"Електронна поща\" name=\"email\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"number\" class=\"form-control\" id=\"age\" placeholder=\"Години\" name=\"age\" value=\"0\">")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"city\" placeholder=\"Град\" name=\"city\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"interests\" placeholder=\"Опишете Вашите интереси\" name=\"interests\" value=\"\">")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"experience\" placeholder=\"Опишете Вашият опит\" name=\"experience\" value=\"\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Регистрирай</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../'\">Отказ</button>")));
    }

    @Test
    void testUserAccessDenied() throws Exception {
        this.mockMvc.perform(get("/access-denied")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2 class=\"text-center p-4 text-white \">НЯМАТЕ ПРАВА ЗА ДОСТЪП ДО СТРАНИЦАТА!</h2>")));
    }
}

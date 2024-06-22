//package digital.razgrad.LMP;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.core.StringContains.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CourseControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Test
//    void testListAllCourse() throws Exception {
//        this.mockMvc.perform(get("/course/list")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("<th scope=\"col\">Курс</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Описание</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Модули</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Тип</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Статус</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Начална дата</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Град</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Лектори</th>")))
//                .andExpect(content().string(containsString(" <input class=\"btn btn-success btn-form\" type=\"submit\" value=\"Кандидатсвай\" />")));
//    }
//}

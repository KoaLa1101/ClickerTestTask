package ru.itlab.clickertests.provider;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import ru.itlab.clickertests.test.InvalidNumberProvider;
import ru.itlab.clickertests.entity.OwnerVDTO;
import ru.itlab.clickertests.entity.RomanVDTO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Service is working when")
public class RomanIntegrationTest {
    private final String BASE_URI = "http://84.201.166.40:8080";

    @Autowired
    RestTemplate restTemplate;

    // idk about the name of method...
    @Nested
    @DisplayName("cast() is working when")
    class ForCast {
        @ParameterizedTest(name = "throws 400 on {0}")
        @ArgumentsSource(value = InvalidNumberProvider.class)
        public void on_problems_numbers_expect_400(int problemNumber) {
            RomanVDTO romanVDTO = restTemplate.getForObject(BASE_URI + "/roman/" + problemNumber, RomanVDTO.class);

            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), romanVDTO.getCode().intValue());
        }

        @ParameterizedTest(name = "return <OK> on {0}")
        @ArgumentsSource(value = ValidNumberProvider.class)
        public void on_valid_numbers_expect_ok(int validNumber){
            RomanVDTO romanVDTO = restTemplate.getForObject(BASE_URI + "/roman/" + validNumber, RomanVDTO.class);

            Assertions.assertEquals(HttpStatus.OK.value(), romanVDTO.getCode().intValue());
        }

        @ParameterizedTest(name = "return {1} on {0}")
        @CsvSource(value = {"1, I", "2, II", "49, XLIX", "50, L", "23, XXIII", "28, XXVIII"})
        public void return_correct_value(int validNumber, String expected){
            RomanVDTO romanVDTO = restTemplate.getForObject(BASE_URI + "/roman/" + validNumber, RomanVDTO.class);
            String actual = romanVDTO.getValue();

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("setOwner() is working when")
    class ForSetOwner {
        private OwnerVDTO owner = new OwnerVDTO("Test", "Advanced");
        @Test
        public void return_201_after_send_owner() {
            HttpEntity<OwnerVDTO> request = new HttpEntity<>(owner, null);

            Assertions.assertEquals(restTemplate.postForEntity(BASE_URI + "/roman/owner", request, RomanVDTO.class).getStatusCode(), HttpStatus.CREATED);
        }

        @ParameterizedTest(name = "return <true> on {0}")
        @ArgumentsSource(value = ValidNumberProvider.class)
        public void on_valid_number_return_my_owner(int validNumber){
            RomanVDTO romanVDTO = restTemplate.getForObject(BASE_URI + "/roman/" + validNumber, RomanVDTO.class);

            Assertions.assertEquals(romanVDTO.getOwner(), owner.toString());
        }

        @ParameterizedTest(name = "return <null owner> on {0}")
        @ArgumentsSource(value = InvalidNumberProvider.class)
        public void on_invalid_numbers_return_null_owner(int invalidNumber){
            RomanVDTO romanVDTO = restTemplate.getForObject(BASE_URI + "/roman/" + invalidNumber, RomanVDTO.class);

            Assertions.assertEquals(romanVDTO.getOwner(), null);
        }
    }


}

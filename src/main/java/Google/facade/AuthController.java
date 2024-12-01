package Google.facade;
import java.util.Optional;

import Google.dto.CredentialsDTO;
import Google.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Authorization Controller", description = "Google authentication operations")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Email checking endpoint
    @Operation(
            summary = "Check if email is registered",
            description = "Checks if the email is already registered in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK: Email is registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials"),
            }
    )
    @RequestMapping("/checking")
    public ResponseEntity<Void> checkUserExists(
            @Parameter(name = "email", description = "Email to check", required = true)
            @RequestParam String email) {
        Optional<Boolean> response = authService.checkUserExists(email);
        if (response.equals(Optional.of(true))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    // Identification endpoint
    @Operation(
            summary = "Identification of the user",
            description = "Identification of the user by checking the email and password.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK: User validated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials"),
            }
    )
    @PostMapping("/identification")
    public ResponseEntity<Void> userAuth(
            @Parameter(name="credentials", description="User's login credentials", required = true)
            @RequestBody CredentialsDTO credentials
    ) {
        Optional<Boolean> response = authService.userAuth(credentials.getEmail(), credentials.getPassword());
        if (response.equals(Optional.of(true))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}

package Google.facade;
import java.util.Optional;

import Google.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
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
        if (response.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    // Identification endpoint
    @Operation(
            summary = "Identificate user",
            description = "Identificates the user by checking the email and password.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK: User validated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials"),
            }
    )
    @PostMapping("/identification")
    public ResponseEntity<Void> userAuth(
            @Parameter(name = "email", description = "User's email", required = true)
            @RequestParam String email,
            @Parameter(name = "password", description = "User's password", required = true)
            @RequestParam String password) {
        Optional<Boolean> response = authService.userAuth(email, password);
        if (response.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}

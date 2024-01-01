package com.nexle.test.entrance.controller;

import com.nexle.test.entrance.config.Constants;
import com.nexle.test.entrance.config.JwtTokenUtil;
import com.nexle.test.entrance.domain.Tokens;
import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.JwtResponseDTO;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.dto.SignUpOutDTO;
import com.nexle.test.entrance.service.JwtUserDetailsService;
import com.nexle.test.entrance.service.TokensService;
import com.nexle.test.entrance.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TokensService tokensService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /***
     *
     * @return
     */
    @GetMapping("/getAll")
    public List<Users> getAllUsers(){
    List<Users> users = usersService.findAll();
        return users;
    }

    @GetMapping("/get-All-Token")
    public List<Tokens> getAllTokens(){

        return tokensService.findAll();
    }

    /***
     *
     * @param signUpInfoDTO
     * @param result
     * @return
     */
    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<Object> saveUser(@Valid @RequestBody SignUpInfoDTO signUpInfoDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Optional<Users> existingUser = usersService.findOneByEmail(signUpInfoDTO.getEmail());
        if (existingUser.isPresent()) {
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.EMAIL_EXIST);
        }
        SignUpOutDTO signUpOutDTO = usersService.createUser(signUpInfoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpOutDTO);
    }

    /***
     *
     * @param signUpInfoDTO
     * @param result
     * @return
     */
    @PostMapping("/sign-in")
    @Transactional
    public ResponseEntity<Object> signUp(@Valid @RequestBody SignUpInfoDTO signUpInfoDTO, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
       if (!authenticate(signUpInfoDTO.getEmail(), signUpInfoDTO.getPassword())){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.LOGIN_FAILED);
       };
        JwtResponseDTO jwtResponseDTO = userDetailsService.generateToken(signUpInfoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDTO);
    }

    /***
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/sign-out")
    @Transactional
    public ResponseEntity<Object> signOut(HttpServletRequest request) throws Exception {
        String email = jwtTokenUtil.getEmailFromToken(request.getHeader(Constants.AUTHORIZATION));
       Users users =  usersService.findOneByEmail(email).get();
        tokensService.deleteAllByUserId(users.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Log Out");
    }

    /***
     *
     * @param refreshToken
     * @return
     * @throws Exception
     */
    @PostMapping("/refresh-token")
    @Transactional
    public ResponseEntity<Object> refreshToken(String refreshToken) throws Exception {
        if (refreshToken == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.REFRESH_TOKEN_EXIST);
        }
        String email = jwtTokenUtil.getEmailFromToken(refreshToken);
        Users users =  usersService.findOneByEmail(email).get();
        SignUpInfoDTO signUpInfoDTO = new SignUpInfoDTO(email, users.getHash());
        JwtResponseDTO jwtResponseDTO = userDetailsService.generateToken(signUpInfoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponseDTO);
    }

    /***
     *
     * @param username
     * @param password
     * @throws Exception
     */
    private boolean authenticate(String username, String password) {
        boolean flag = true;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            flag = false;
        } catch (BadCredentialsException e) {
            flag = false;
        }
        return flag;
    }
}

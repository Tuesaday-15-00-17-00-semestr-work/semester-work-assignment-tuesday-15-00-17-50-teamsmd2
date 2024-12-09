//package com.librer.Librer.sexurity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.librer.Librer.user.UserController;
//import org.springframework.security.core.userdetails.User;
//import com.librer.Librer.user.UserRepository;
//import com.librer.Librer.user.UserRecord;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService{
//    private UserRepository userRep;
//
//    @Autowired
//    public CustomUserDetailsService(UserRepository userRep) {
//        this.userRep = userRep;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserRecord newUser = userRep.findByEmail(email);
//        //UserDetails newDet = UserDetails.class.cast(newUser);
//        UserDetails retUser = User.withUsername(newUser.email())
//                .password(newUser.password())
//                .roles(String.valueOf(newUser.role_id()))
//                .build();
//        return retUser;
//    }
//
//}
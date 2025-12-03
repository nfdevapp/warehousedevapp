//package org.example.backend.security;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DirtiesContext
//    @WithMockUser
//    void getMe() throws Exception {
//        mockMvc.perform(get("/api/auth/me")
//                        .with(oidcLogin()
//                                .userInfoToken(token -> token
//                                        .claim("login", "testUser")
//                                        .claim("avatar_url", "testAvatarUrl")
//                                ))
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().json("""
//                    {
//                        "username": "testUser",
//                        "avatarUrl": "testAvatarUrl"
//                    }
//                    """));
//    }
//}

package bg.softuni.comments.web;

import bg.softuni.comments.model.Comment;
import bg.softuni.comments.model.dto.CommentDTO;
import bg.softuni.comments.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerT {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
    }

    @Test
    void testAddComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Great book!");

        MvcResult result = mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        int id = JsonPath.read(body, "$.id");

        Optional<Comment> createdCommentOpt = commentRepository.findById((long) id);

        assertTrue(createdCommentOpt.isPresent());

        Comment createdComment = createdCommentOpt.get();

        assertEquals("Great book!", createdComment.getText());
    }

    @Test
    void testDeleteComent() throws Exception {
        Comment comment = new Comment();
        comment.setText("Great book!");
        comment = commentRepository.save(comment);

        mockMvc.perform(delete("/api/comments/{id}", comment.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCommentsByBookId() throws Exception {
        Long bookId = 1L;

        Comment comment1 = new Comment();
        comment1.setText("Great book!");

        comment1.setBookId(bookId);

        Comment comment2 = new Comment();
        comment2.setText("Enjoyed reading it.");
        comment2.setBookId(bookId);

        commentRepository.saveAll(Arrays.asList(comment1, comment2));

        mockMvc.perform(get("/api/comments/book/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Great book!"))
                .andExpect(jsonPath("$[1].text").value("Enjoyed reading it."));
    }
}

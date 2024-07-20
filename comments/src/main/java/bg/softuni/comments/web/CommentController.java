package bg.softuni.comments.web;

import bg.softuni.comments.mapper.CommentMapper;
import bg.softuni.comments.model.Comment;
import bg.softuni.comments.model.dto.CommentDTO;
import bg.softuni.comments.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO) {
        Comment savedComment = commentService.addComment(commentDTO);
        return ResponseEntity.ok(CommentMapper.toDto(savedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Comment>> getCommentsByBookId(@PathVariable Long bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        return ResponseEntity.ok(comments);
    }
}

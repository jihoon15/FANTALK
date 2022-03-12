package com.ssafy.api.controller;

import com.ssafy.api.request.CommentRegisterPostReq;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.CommentService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommentRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.NoSuchElementException;


/**
 * 댓글 컨트롤러 정의
 */
@Api(value = "댓글 API", tags = {"Comment"})
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/articles/{articleId}/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록")
    public ResponseEntity<String> registerComment(
            @RequestBody @ApiParam(value="댓글 정보", required = true) CommentRegisterPostReq commentInfo,
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId,
            @ApiIgnore Authentication authentication) {
        logger.info("registerComment 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        try {
            Article article = articleService.findById(articleId);
            commentService.registerComment(commentInfo, article, user);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

        } catch (NoSuchElementException e){
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e){
            return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 204, message = "회원 불일치(NOT SAME USER)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제")
    public ResponseEntity<String> deleteArticle(
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId,
            @ApiIgnore Authentication authentication) {
        logger.info("deleteComment 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        try {
            Comment comment = commentRepository.findByIdAndArticleId(commentId, articleId).get();
            if (comment.getUser().getId().equals(user.getId())) {
                commentService.deleteComment(comment);
                return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("NOT SAME USER", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);
        }

    }
}

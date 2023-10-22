package controller;
@RestController
@RequestMapping("/v1/todo")
public class TodoController {

    private final TodoServiceImpl todoService;

    @GetMapping("/findByDate/{date}")
    public Todo findByDate(..) {}

    @PostMapping
    public Todo
}

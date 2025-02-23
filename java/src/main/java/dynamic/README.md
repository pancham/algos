## 3 Keys of Backtracking
* Choices
* Constraints
* Goal
## Recipe for backtracking technique
````
class Backtrack {
    // collect responses. IMPORTANT not to pass it as method argument because
    // it will increase the stack size and may cause stack overflow error early
    T response;
    
    void backtrack(args) {
        if (GOAL REACHED)
            add solution to response
            return
            
        for (int i = 0; i < CHOICES; i++)
            if (CHOICES[i] is valid)
                make CHOICES[i] (update any state, particularly in args, OPTIONAL)
                
                backtrack(args)
                
                undo state (OPTIONAL)
                
    }
}
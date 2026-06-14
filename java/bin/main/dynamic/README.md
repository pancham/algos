## 4 Keys of Backtracking
* Choices
* Constraints
* Goal
* Memorization
## Recipe for backtracking technique
````
class Backtrack {
    // collect responses. IMPORTANT not to pass it as method argument because
    // it will increase the stack size and may cause stack overflow error early
    T response;
    HashMap mem;
    
    void fn(args) {
        if (present in mem)
            return from mem
            
        if (GOAL REACHED)
            add to mem
            add solution to response OR return a value
            return
        
        if (GOAL CANNOT BE REACHED)
            add to mem (remember that goal cannot be reached)
            return
        
        for (int i = 0; i < CHOICES; i++)
            if (CHOICES[i] is valid)
                make CHOICES[i] (update any state, particularly in args, OPTIONAL)
                
                fn(args)
                
                undo state (OPTIONAL)
                
    }
}

## DP Patterns
* Fibonacci
* 0/1 Knapsack
* Unbounded Knapsack
* Longest Common Subsequence
* Palindromes
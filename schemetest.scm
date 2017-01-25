;Cole Hannel - CS 117 Project 8 Mergesort in scheme
;Mergesort sorts a list using the mergesort algorithm
;expected input is (mergesort '(some number of numbers))
;Additionally, both the split and merge functions can be called individually without trouble

;Splits a list into two halves on the odd parts, and is recursive
;Final output looks like ((one list) another list), '(1 2 3 4)->((1 3) 2 4)
;Takes the first two parts of an entered list, puts one in one list and one in the other, then each part has two
;recursive calls that perform the same operation
(define (split x)
	( cond ((null? x) (cons `()`()) )
		   ((null? (cdr x)) ( cons x `()) )
	( else ( cons ( cons (car x) (car ( split (cddr x)))) ( cons (car (cdr x)) (cdr (split (cddr x)))))
	)

	)
)

;Merge does exactly what the name implies. Merges two lists together. If the two lists being merged are ordered, then the 
;Final list output will be ordered. However if they are not then the final output will not be.
;Expected input is (merge '(list) '(list)) and will output one (list) ordered.
(define (merge x y)         
   (cond ( (null? x) y)
         ( (null? y) x)
         ( (< (car x)(car y))
              (cons (car x) (merge (cdr x)y)))
         (else (cons (car y) (merge x (cdr y))))
   )
)


;Mergesort is a recursive function that takes a list and returns it ordered
;Calls both merge and split in its quest to put smaller numbers on the left side of bigger numbers
;Expected input is (mergesort '(list)) and the output is that list ordered
(define (mergesort x)
  (cond ((null? x)  `())
        ((= 1 (length x)) x)
        ((= 2 (length x)) (merge (list (car x))(cdr x)))
        (else (merge (mergesort (car (split x)) ) (mergesort (cdr (split x)) )))
  )
)




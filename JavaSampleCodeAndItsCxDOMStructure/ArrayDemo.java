/*
* In this sample code, the following CxDOM Types are covered:
* 4 ArrayCreateExpr
* 6 ArrayInitializer
* 8 AssignExpr
* 16 BinaryExpr
* 26 ClassDecl
* 38 Declarator
* 60 IndexerRef
* 61 IntegerLiteral
* 70 MemberAccess
* 72 MethodDecl
* 73 MethodInvokeExpr
* 74 MethodRef
* 77 NullLiteral
* 81 Param
* 82 ParamDecl
* 90 RankSpecifier
* 95 StringLiteral
* 108 TypeRef
* 112 UnknownReference
* 115 VariableDeclStmt
*
*
* Please check ArrayDemo.png for the whole DOM tree of this file.
*/

// Sample code comes from https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html

// ArrayDemo | ClassDecl
class ArrayDemo {

    // Type References | TypeRefCollection,
    // Members | MemberDeclCollection,

    // main | MethodDecl
    // void | TypeRef
    // Parameter Declarations | ParamDeclCollection
    // args | ParamDecl
    // string | TypeRef
    // [] | RankSpecifier
    // Statements | StatementCollection
    public static void main(String[] args) {

        // VariableDeclStmt | VariableDeclStmt
        // int | TypeRef
        // [] RankSpecifier
        // anArray | Declarator
        // null | NullLiteral
        // declares an array of integers
        int[] anArray;

        // ExprStmt | ExprStmt
        // AssignExpr | AssignExpr
        // anArray | UnknownReference
        // ArrayCreateExpr | ArrayCreateExpr
        // 10 | IntegerLiteral
        // ArrayInitializer | ArrayInitializer
        // int | TypeRef
        // [] | RankSpecifier
        // allocates memory for 10 integers
        anArray = new int[10];

        // ExprStmt | ExprStmt
        // AssignExpr | AssignExpr
        // anArray | IndexerRef
        // anArray | UnknownReference
        // 0 | IntegerLiteral
        // 100 | IntegerLiteral
        // initialize first element
        anArray[0] = 100;

        // ExprStmt | ExprStmt
        // println | MethodInvokeExpr
        // println | MethodRef
        // out | MemberAccess
        // System | UnknownReference
        // Param | Param
        // BinaryExpr | BinaryExpr
        // "Element at index 0: " | StringLiteral
        // anArray | IndexerRef
        // anArray | UnknownReference
        // 0 | IntegerLiteral
        System.out.println("Element at index 0: " + anArray[0]);
    }
}
package com.devsync.analyzeservice.constant;

public class Prompts {
    public static String AnalyzePrompt(String serializedObject){
        return """
    You are a senior software architect and code reviewer.

     Given the following Pull Request data (as a JSON object), analyze the changes from three different perspectives:

     1. **Technical Quality**: Evaluate code readability, maintainability, naming, performance, testability, and best practices.
     2. **Functional Impact**: Evaluate how well the code addresses business logic and user requirements. Check for logic errors or inconsistencies.
     3. **Architectural Impact**: Assess how the changes affect the overall system design, modularity, scalability, and alignment with existing architecture.

     Also, assign a `riskScore` between 0 and 100 (higher means riskier) based on how risky it would be to merge this Pull Request without further review.
     Return your result as a plain, **non-escaped JSON string** (do not use backslashes or escaped characters) with the following structure:
     ```json
     {
       "riskScore": 0-100,
       "technicalComment": "Your analysis here...",
       "functionalComment": "Your analysis here...",
       "architecturalComment": "Your analysis here..."
     }
    Pull Request:
    """ + serializedObject;
    }
}

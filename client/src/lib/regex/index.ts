const emailRegex = /^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/
const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/
const roleRegex = /^(STUDENT|TEACHER)$/
const deleteRegex = /^(delete)$/
const questionTypeRegex = /^(MCQ|MSQ)$/
const questionVisibilityRegex = /^(PUBLIC|PRIVATE)$/

export {
    emailRegex,
    passwordRegex, 
    roleRegex, 
    deleteRegex, 
    questionTypeRegex, 
    questionVisibilityRegex
}
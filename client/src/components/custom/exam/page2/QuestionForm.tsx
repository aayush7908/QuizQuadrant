// "use client"

// import { Input } from "@/components/ui/input"
// import { useContext, useEffect } from "react"
// import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
// import { Textarea } from "@/components/ui/textarea"
// import { SubjectContext } from "@/context/subject/SubjectContext"
// import { Question } from "@/lib/type/model/Question"
// import { Label } from "@/components/ui/label"
// import { Exam } from "@/lib/type/model/Exam"
// import { Button } from "@/components/ui/button"
// import { Trash2 } from "lucide-react"
// import { useToast } from "@/components/ui/use-toast"

// export default function QuestionForm({
//     questionIndex,
//     exam,
//     changeExam
// }: {
//     questionIndex: number,
//     exam: Exam,
//     changeExam: Function
// }) {

//     const { subjects } = useContext(SubjectContext);

//     const handleEditSelectedQuestion = () => {
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex].id = "";
//         changeExam(newExam);
//     }

//     const handleChangePositiveMarks = ({ target }: { target: { value: string } }) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.positiveMarks = Number(target.value);
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeNegativeMarks = ({ target }: { target: { value: string } }) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.negativeMarks = Number(target.value);
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeType = (value: string) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.type = value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeSubject = (value: string) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.subtopic.subject.id = value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeSubtopic = (value: string) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.subtopic.id = value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeQuestionStatement = ({ target }: { target: { value: string } }) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.statement = target.value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeOptionStatement = ({ target }: { target: { value: string } }, optionIndex: number) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.options[optionIndex].statement = target.value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeOptionIsCorrectMCQ = (index: number) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         for (let i = 0; i < newQuestion.options.length; i++) {
//             newQuestion.options[i].isCorrect = false;
//         }
//         newQuestion.options[index].isCorrect = true;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeOptionIsCorrectMSQ = (index: number) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.options[index].isCorrect = !newQuestion.options[index].isCorrect;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     const handleChangeSolutionStatement = ({ target }: { target: { value: string } }) => {
//         const newQuestion = { ...exam.questions[questionIndex] } as Question;
//         newQuestion.solution.statement = target.value;
//         const newExam = { ...exam } as Exam;
//         newExam.questions[questionIndex] = newQuestion;
//         changeExam(newExam);
//     }

//     useEffect(() => { }, [questionIndex])

//     return (
//         <div className="grid gap-4">
//             <div className={`py-2 px-3 text-sm bg-muted border rounded-md gap-3 justify-between items-center ${Boolean(exam.questions[questionIndex].id) ? "grid md:flex" : "hidden"}`}>
//                 <span>
//                     <b>NOTE: </b>
//                     <span>This is a selected question. So, you can edit only marks for this question. If you want, you can edit, but this will create a new question, rather than linking the selected one.</span>
//                 </span>
//                 <div className="flex justify-center">
//                     <Button onClick={handleEditSelectedQuestion}>Edit</Button>
//                 </div>
//             </div>
//             <div className="grid sm:grid-cols-2 gap-4">
//                 <div className="grid grid-cols-2 gap-2 ">
//                     <div>
//                         <Label>Positive Marks</Label>
//                         <Input
//                             value={exam.questions[questionIndex].positiveMarks}
//                             onChange={(e) => { handleChangePositiveMarks(e) }}
//                             type="number"
//                             required
//                         />
//                     </div>
//                     <div>
//                         <Label>Negative Marks</Label>
//                         <Input
//                             value={exam.questions[questionIndex].negativeMarks}
//                             onChange={(e) => { handleChangeNegativeMarks(e) }}
//                             type="number"
//                             required
//                         />
//                     </div>
//                 </div>
//                 <div className="grid gap-2">
//                     <Label>Question Type</Label>
//                     <Select
//                         onValueChange={(e) => { handleChangeType(e) }}
//                         value={exam.questions[questionIndex].type}
//                         disabled={Boolean(exam.questions[questionIndex].id)}
//                     >
//                         <SelectTrigger>
//                             <SelectValue placeholder="Select question type" />
//                         </SelectTrigger>
//                         <SelectContent>
//                             <SelectGroup>
//                                 <SelectLabel>Select question type</SelectLabel>
//                                 <SelectItem value="MCQ">MCQ</SelectItem>
//                                 <SelectItem value="MSQ">MSQ</SelectItem>
//                             </SelectGroup>
//                         </SelectContent>
//                     </Select>
//                 </div>
//             </div>
//             <div className="grid sm:grid-cols-2 gap-4">
//                 <div className="grid gap-2">
//                     <Label>Subject</Label>
//                     <Select
//                         onValueChange={(e) => { handleChangeSubject(e) }}
//                         value={exam.questions[questionIndex].subtopic.subject.id}
//                         disabled={Boolean(exam.questions[questionIndex].id)}
//                     >
//                         <SelectTrigger>
//                             <SelectValue placeholder="Select a subject" />
//                         </SelectTrigger>
//                         <SelectContent>
//                             <SelectGroup>
//                                 <SelectLabel>Select a subject</SelectLabel>
//                                 {
//                                     subjects && (
//                                         Array.from(subjects.values()).map((subject, index) => {
//                                             return (
//                                                 <SelectItem key={index} value={subject.id}>
//                                                     {subject.name}
//                                                 </SelectItem>
//                                             )
//                                         })
//                                     )
//                                 }
//                             </SelectGroup>
//                         </SelectContent>
//                     </Select>
//                 </div>
//                 <div className="grid gap-2">
//                     <Label>Subtopic</Label>
//                     <Select
//                         onValueChange={(e) => { handleChangeSubtopic(e) }}
//                         value={exam.questions[questionIndex].subtopic.id}
//                         disabled={Boolean(exam.questions[questionIndex].id)}
//                     >
//                         <SelectTrigger>
//                             <SelectValue placeholder="Select a subtopic" />
//                         </SelectTrigger>
//                         <SelectContent>
//                             <SelectGroup>
//                                 <SelectLabel>Select a subtopic</SelectLabel>
//                                 {
//                                     subjects && (
//                                         subjects.get(exam.questions[questionIndex].subtopic.subject.id)?.subtopics.map((subtopic, index) => {
//                                             return (
//                                                 <SelectItem key={index} value={subtopic.id}>
//                                                     {subtopic.name}
//                                                 </SelectItem>
//                                             )
//                                         })
//                                     )
//                                 }
//                             </SelectGroup>
//                         </SelectContent>
//                     </Select>
//                 </div>
//             </div>
//             <div className="grid gap-2">
//                 <Label>Question</Label>
//                 <Textarea
//                     placeholder="Enter question here ..."
//                     value={exam.questions[questionIndex].statement}
//                     disabled={Boolean(exam.questions[questionIndex].id)}
//                     onChange={(e) => { handleChangeQuestionStatement(e) }}
//                     rows={5}
//                     required
//                 />
//             </div>
//             {
//                 exam.questions[questionIndex].options.map((option, index) => {
//                     return (
//                         <div className="grid gap-2">
//                             <Label>Option - {index + 1}</Label>
//                             <Textarea
//                                 placeholder={`Enter option - ${index + 1} here ...`}
//                                 value={option.statement}
//                                 disabled={Boolean(exam.questions[questionIndex].id)}
//                                 onChange={(e) => { handleChangeOptionStatement(e, index) }}
//                                 rows={5}
//                                 required
//                             />
//                         </div>
//                     )
//                 })
//             }
//             <div className="grid gap-2">
//                 <p className="text-sm font-medium">Select correct option(s)</p>
//                 {
//                     exam.questions[questionIndex].type === "MCQ" && (
//                         exam.questions[questionIndex].options.map((option, index) => {
//                             return (
//                                 <div
//                                     key={index}
//                                     onClick={() => { handleChangeOptionIsCorrectMCQ(index) }}
//                                     className={`py-2 px-4 rounded-md cursor-pointer border ${option.isCorrect && "bg-green-400"} ${Boolean(exam.questions[questionIndex].id) && "pointer-events-none"}`}
//                                 >
//                                     {option.statement}
//                                 </div>
//                             )
//                         })
//                     )
//                 }
//                 {
//                     exam.questions[questionIndex].type === "MSQ" && (
//                         exam.questions[questionIndex].options.map((option, index) => {
//                             return (
//                                 <div
//                                     key={index}
//                                     onClick={() => { handleChangeOptionIsCorrectMSQ(index) }}
//                                     className={`py-2 px-4 rounded-md cursor-pointer border ${option.isCorrect && "bg-green-400"} ${Boolean(exam.questions[questionIndex].id) && "pointer-events-none"}`}
//                                 >
//                                     {option.statement}
//                                 </div>
//                             )
//                         })
//                     )
//                 }
//                 {
//                     (exam.questions[questionIndex].type !== "MCQ" && exam.questions[questionIndex].type !== "MSQ") && (
//                         <div className={`py-2 px-4 rounded-md border bg-red-300`}>
//                             Select question type first
//                         </div>
//                     )
//                 }
//             </div>
//             <div className="grid gap-2">
//                 <Label>Solution</Label>
//                 <Textarea
//                     placeholder="Enter solution here ..."
//                     value={exam.questions[questionIndex].solution.statement}
//                     disabled={Boolean(exam.questions[questionIndex].id)}
//                     onChange={(e) => { handleChangeSolutionStatement(e) }}
//                     rows={5}
//                     required
//                 />
//             </div>
//         </div>
//     )
// }

// "use client"

// import { Button } from "@/components/ui/button";
// import { FormField } from "@/components/ui/form";
// import { Input } from "@/components/ui/input";
// import { Label } from "@/components/ui/label";
// import { useToast } from "@/components/ui/use-toast";
// import { emailRegex } from "@/lib/regex";
// import { Exam } from "@/lib/type/model/Exam"
// import { User } from "@/lib/type/model/User";
// import { Trash2 } from "lucide-react";
// import { useEffect, useState } from "react";

// export function Page3({
//     exam,
//     changeExam
// }: {
//     exam: Exam,
//     changeExam: Function
// }) {

//     const [emailInput, setEmailInput] = useState<string>("");
//     const [emails, setEmails] = useState<Set<string>>(new Set());
//     const { toast } = useToast();

//     const handleChangeEmailInput = ({ target }: { target: { value: string } }) => {
//         setEmailInput(target.value);
//     }

//     const handleChangeCandidate = () => {
//         const emailArr: string[] = emailInput.split(",");
//         let invalidEmails: string = "";
//         for (let i = 0; i < emailArr.length; i++) {
//             emailArr[i] = emailArr[i].trim();
//             if (emailRegex.test(emailArr[i])) {
//                 emails.add(emailArr[i]);
//             } else {
//                 invalidEmails += emailArr[i] + ", ";
//             }
//         }
//         if (invalidEmails.length > 0) {
//             invalidEmails = invalidEmails.slice(0, invalidEmails.length - 2);
//             toast({
//                 title: "Invalid emails found",
//                 description: "Some invalid emails are not added. Correct them and retry.",
//                 variant: "destructive"
//             });
//         }
//         const newExam = { ...exam } as Exam;
//         const newCandidates = [] as User[];
//         emails.forEach((email) => {
//             newCandidates.push({ email: email } as User);
//         });
//         newExam.candidates = newCandidates;
//         setEmailInput(invalidEmails);
//         changeExam(newExam);
//     }

//     const handleDeleteCandidate = (index: number) => {
//         const isConfirm = window.confirm(`Are you sure to remove ${exam.candidates[index].email} ?`);
//         if (!isConfirm) {
//             return;
//         }
//         const newExam = { ...exam } as Exam;
//         newExam.candidates = exam.candidates.filter((candidate, i) => {
//             return (index !== i);
//         });
//         changeExam(newExam);
//     }

//     useEffect(() => {
//         const newEmails = new Set<string>();
//         exam.candidates.forEach((candidate) => {
//             newEmails.add(candidate.email);
//         })
//         setEmails(newEmails);
//     }, []);

//     return (
//         <div className="flex flex-col gap-[2rem]">
//             <div className="grid md:flex gap-2 items-center">
//                 <div className="w-full">
//                     <Label>Candidate Emails</Label>
//                     <Input
//                         value={emailInput}
//                         onChange={(e) => { handleChangeEmailInput(e) }}
//                         type="text"
//                         required
//                     />
//                     <Label className="text-sm font-medium text-gray-600"><b>NOTE:</b> You can add comma(,) separated values here</Label>
//                 </div>
//                 <div className="flex justify-end">
//                     <Button onClick={handleChangeCandidate}>Add</Button>
//                 </div>
//             </div>
//             <div className="flex flex-wrap gap-3 mb-[5rem]">
//                 {
//                     exam.candidates.map((candidate: User, index: number) => {
//                         return (
//                             <div key={index} className="max-w-[17rem] xs:max-w-screen-xs overflow-x-auto px-3 flex gap-3 items-center border border-gray-400 rounded-md bg-muted text-lg">
//                                 <span>{candidate.email}</span>
//                                 <Trash2 className="size-5 cursor-pointer shrink-0" onClick={() => { handleDeleteCandidate(index) }} />
//                             </div>
//                         )
//                     })
//                 }
//             </div>
//         </div>
//     );
// }

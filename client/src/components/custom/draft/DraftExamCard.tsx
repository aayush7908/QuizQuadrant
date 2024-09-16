// "use client"

// import { Card, CardContent, CardTitle } from "@/components/ui/card";
// import Link from "next/link";
// import { Exam } from "@/lib/type/model/Exam";
// import { SubmitButton } from "../SubmitButton";
// import { useToast } from "@/components/ui/use-toast";
// import { useState } from "react";
// import { deleteDraftExamAPI } from "@/actions/draft/exam/delete";

// export function DraftExamCard({ data, index, removeFunction }: { data: Exam, index: number, removeFunction(index: number): void }) {

//     const [isProcessing, setIsProcessing] = useState<boolean>(false);
//     const { toast } = useToast();

//     const handleDeleteDraftExam = async () => {
//         const isConfirm = window.confirm("All data related to this draft will be deleted and cannot be retrieved back again. Are you sure to delete this Draft Exam ?");
//         if (!isConfirm) {
//             return;
//         }
//         const str = window.prompt("Write delete below to confirm.");
//         if (str !== "delete") {
//             toast({
//                 title: "Action aborted",
//                 variant: "destructive"
//             });
//             return;
//         }
//         setIsProcessing(true);
//         const { success, error } = await deleteDraftExamAPI(data.id);
//         if (success) {
//             toast({
//                 title: "Draft deleted successfully"
//             });
//             removeFunction(index);
//         } else if (error) {
//             toast({
//                 title: error.errorMessage,
//                 variant: "destructive"
//             });
//         }
//         setIsProcessing(false);
//     }

//     return (
//         <Card className="px-3 py-2 grid xs:flex gap-3 justify-between items-center">
//             <CardTitle>
//                 <span
//                     className="text-lg"
//                 >
//                     {data.title ? data.title.slice(0, 20) + `${data.title.length > 20 ? "..." : ""}` : "Untitled Draft Exam"}
//                 </span>
//                 <p className="text-base font-medium text-gray-600">
//                     Last Modified On: {(new Date(data.lastModifiedOn)).toLocaleString()}
//                 </p>
//             </CardTitle>
//             <CardContent className="p-0 xs:justify-center">
//                 <div className="flex gap-3">
//                     <Link
//                         href={`/exam/draft/${data.id}`}
//                         className="px-3 py-2 bg-black text-white rounded-md"
//                     >
//                         Edit
//                     </Link>
//                     <SubmitButton
//                         displayName="Delete"
//                         variant="destructive"
//                         type="button"
//                         isProcessing={isProcessing}
//                         onSubmit={handleDeleteDraftExam}
//                     />
//                 </div>
//             </CardContent>
//         </Card>
//     );
// }
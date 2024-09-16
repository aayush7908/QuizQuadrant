// "use client"

// import { getDraftExamByIdAPI } from "@/actions/draft/exam/get/by-id";
// import { createExamAPI } from "@/actions/exam/create";
// import { ExamForm } from "@/components/custom/exam/ExamForm";
// import { Loader } from "@/components/custom/Loader";
// import { useToast } from "@/components/ui/use-toast";
// import { Exam } from "@/lib/type/model/Exam";
// import { useEffect, useState } from "react";

// export default function DraftExam({ params }: { params: { id: string } }) {

//     const [isProcessing, setIsProcessing] = useState<boolean>(false);
//     const [exam, setExam] = useState<Exam | undefined>(undefined);
//     const { toast } = useToast();

//     const onSubmit = async (data: Exam) => {
//         return await createExamAPI(data);
//     }

//     useEffect(() => {
//         (async () => {
//             setIsProcessing(true);
//             const { success, data, error } = await getDraftExamByIdAPI(params.id);
//             if (success && data) {
//                 setExam(data);
//             } else if (error) {
//                 toast({
//                     title: error.errorMessage,
//                     variant: "destructive"
//                 });
//             }
//             setIsProcessing(false);
//         })();
//     }, []);

//     return (
//         <>
//             {
//                 isProcessing && (
//                     <Loader />
//                 )
//             }
//             {
//                 exam && (
//                     <ExamForm
//                         successMessage="Exam created successfully"
//                         onSubmit={onSubmit}
//                         defaultFormData={exam}
//                     />
//                 )
//             }
//         </>
//     );
// }

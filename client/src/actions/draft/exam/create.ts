// "use client"

// import { getToken } from "@/lib/cookie-store";
// import { serverEnv } from "@/lib/env/server";
// import { Exam } from "@/lib/type/model/Exam";
// import { Question } from "@/lib/type/model/Question";
// import { error } from "@/lib/type/response/error/error";
// import { Id } from "@/lib/type/response/id/id";

// const createExamDraftAPI = async (body: Exam) => {
//     try {
//         // extract token from cookies
//         const token: string | undefined = getToken();
//         if (!token) {
//             return {
//                 success: true
//             };
//         }

//         // API call
//         const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/draft/exam/create`, {
//             method: "POST",
//             cache: "no-cache",
//             headers: {
//                 "Content-Type": "application/json",
//                 "Authorization": `Bearer ${token}`
//             },
//             body: JSON.stringify(body)
//         });

//         // if successfull
//         if (res.status === 200) {
//             const data: Id = await res.json();
//             return {
//                 success: true,
//                 data: data
//             };
//         }

//         // if error
//         const data: error = await res.json();
//         return {
//             success: false,
//             error: data
//         };

//     } catch (err) {
//         return {
//             success: false,
//             error: {
//                 errorMessage: "Some Error Occurred"
//             } as error
//         };
//     }
// }

// export {
//     createExamDraftAPI
// }
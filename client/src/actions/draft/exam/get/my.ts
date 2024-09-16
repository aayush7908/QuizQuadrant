// "use client"

// import { getToken } from "@/lib/cookie-store";
// import { serverEnv } from "@/lib/env/server";
// import { Exam } from "@/lib/type/model/Exam";
// import { error } from "@/lib/type/response/error/error";

// const pageSize: number = 5;

// const getMyDraftExamsAPI = async (pageNumber: number) => {
//     try {
//         // extract token from cookies
//         const token: string | undefined = getToken();
//         if (!token) {
//             return {
//                 success: true
//             };
//         }

//         // API call
//         const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/draft/exam/get/my?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
//             method: "GET",
//             cache: "no-cache",
//             headers: {
//                 "Content-Type": "application/json",
//                 "Authorization": `Bearer ${token}`
//             }
//         });

//         // if successfull
//         if (res.status === 200) {
//             const data: Array<Exam> = await res.json();
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
//     getMyDraftExamsAPI
// }
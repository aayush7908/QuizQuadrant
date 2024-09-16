// "use client"

// import { getToken } from "@/lib/cookie-store";
// import { serverEnv } from "@/lib/env/server";
// import { error } from "@/lib/type/response/error/error";

// const deleteDraftExamAPI = async (id: string) => {
//     try {
//         // extract token from cookies
//         const token: string | undefined = getToken();
//         if (!token) {
//             return {
//                 success: true
//             };
//         }

//         // API call
//         const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/draft/exam/delete/${id}`, {
//             method: "DELETE",
//             cache: "no-cache",
//             headers: {
//                 "Content-Type": "application/json",
//                 "Authorization": `Bearer ${token}`
//             }
//         });

//         // if successfull
//         if (res.status === 200) {
//             return {
//                 success: true
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
//     deleteDraftExamAPI
// }
(()=>{var e={};e.id=571,e.ids=[571],e.modules={7849:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external")},2934:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external.js")},5403:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external")},4580:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external.js")},4749:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external")},5869:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external.js")},399:e=>{"use strict";e.exports=require("next/dist/compiled/next-server/app-page.runtime.prod.js")},5330:(e,t,r)=>{"use strict";r.r(t),r.d(t,{GlobalError:()=>n.a,__next_app__:()=>d,originalPathname:()=>l,pages:()=>p,routeModule:()=>x,tree:()=>u}),r(9448),r(2240),r(7629),r(2523);var a=r(3191),s=r(8716),i=r(7922),n=r.n(i),c=r(5231),o={};for(let e in c)0>["default","tree","pages","GlobalError","originalPathname","__next_app__","routeModule"].indexOf(e)&&(o[e]=()=>c[e]);r.d(t,o);let u=["",{children:["practice",{children:["subject",{children:["[id]",{children:["__PAGE__",{},{page:[()=>Promise.resolve().then(r.bind(r,9448)),"E:\\PROJECTS\\quizquadrant\\client\\src\\app\\practice\\subject\\[id]\\page.tsx"]}]},{}]},{}]},{}]},{layout:[()=>Promise.resolve().then(r.bind(r,2240)),"E:\\PROJECTS\\quizquadrant\\client\\src\\app\\layout.tsx"],error:[()=>Promise.resolve().then(r.bind(r,7629)),"E:\\PROJECTS\\quizquadrant\\client\\src\\app\\error.tsx"],"not-found":[()=>Promise.resolve().then(r.bind(r,2523)),"E:\\PROJECTS\\quizquadrant\\client\\src\\app\\not-found.tsx"]}],p=["E:\\PROJECTS\\quizquadrant\\client\\src\\app\\practice\\subject\\[id]\\page.tsx"],l="/practice/subject/[id]/page",d={require:r,loadChunk:()=>Promise.resolve()},x=new a.AppPageRouteModule({definition:{kind:s.x.APP_PAGE,page:"/practice/subject/[id]/page",pathname:"/practice/subject/[id]",bundlePath:"",filename:"",appPaths:[]},userland:{loaderTree:u}})},6838:(e,t,r)=>{Promise.resolve().then(r.bind(r,1053))},1053:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>l});var a=r(326),s=r(4052);let i=async(e,t)=>{try{let r=await fetch(`${s.N.BACKEND_BASE_URL}/question/get/by-subject/${e}?pageNumber=${t}&pageSize=5`,{method:"GET",cache:"no-cache",headers:{"Content-Type":"application/json"}});if(200===r.status){let e=await r.json();return{success:!0,data:e}}let a=await r.json();return{success:!1,error:a}}catch(e){return{success:!1,error:{errorMessage:"Some Error Occurred"}}}};var n=r(6801),c=r(1709),o=r(6206),u=r(6627),p=r(7577);function l({params:e}){let[t,r]=(0,p.useState)(0),[s,l]=(0,p.useState)([]),[d,x]=(0,p.useState)(!0),{toast:g}=(0,u.pm)(),m=async t=>{let{success:r,data:a,error:s}=await i(e.id,t);if(r&&a)return a;s&&g({title:s.errorMessage,variant:"destructive"})};return a.jsx("div",{className:"py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]",children:d?a.jsx(c.a,{}):a.jsx(n.v,{fetchFunction:m,totalLength:t,initialData:s,Component:o.j})})}},9448:(e,t,r)=>{"use strict";r.r(t),r.d(t,{$$typeof:()=>n,__esModule:()=>i,default:()=>c});var a=r(8570);let s=(0,a.createProxy)(String.raw`E:\PROJECTS\quizquadrant\client\src\app\practice\subject\[id]\page.tsx`),{__esModule:i,$$typeof:n}=s;s.default;let c=(0,a.createProxy)(String.raw`E:\PROJECTS\quizquadrant\client\src\app\practice\subject\[id]\page.tsx#default`)}};var t=require("../../../../webpack-runtime.js");t.C(e);var r=e=>t(t.s=e),a=t.X(0,[717,810,741],()=>r(5330));module.exports=a})();
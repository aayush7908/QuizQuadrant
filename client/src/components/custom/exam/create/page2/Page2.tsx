import QuestionForm from "@/components/custom/question/QuestionForm";
import { SideMenu } from "./SideMenu";

export function Page2() {
    return (
        <div className="min-h-full w-full flex">
            <SideMenu />
            <div className="w-full md:w-[calc(100%-17rem)] p-[1rem] md:p-[2rem] lg:p-[3rem] md:ms-[17rem] mb-[4rem]">
                <QuestionForm />
            </div>
        </div>
    );
}
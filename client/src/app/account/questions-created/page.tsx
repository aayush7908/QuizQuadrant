import { QuestionCard } from "@/components/custom/account/questions-created/QuestionCard";
import { Button } from "@/components/ui/button";
import { FilePlus2, Plus, PlusCircle, PlusSquare } from "lucide-react";
import Link from "next/link";

export default function QuestionsCreated() {
    return (
        <div className="min-h-full p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem] grid gap-5">
            <div className="flex justify-end">
                <Link href={"/question/create"} className="flex items-center gap-2 text-base bg-black text-white py-2 px-3 rounded-md cursor-pointer">
                    <PlusSquare />
                    <span>Create Question</span>
                </Link>
            </div>
            <QuestionCard />
            <QuestionCard />
            <QuestionCard />
            <QuestionCard />
        </div>
    );
}
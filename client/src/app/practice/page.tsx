import { QuestionCard } from "@/components/custom/practice/QuestionCard";

export default function Practice() {
    return (
        <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
            <QuestionCard />
            <QuestionCard />
            <QuestionCard />
        </div>
    );
}
import { SubtopicCard } from "@/components/custom/home/SubtopicCard";
import { Card, CardContent } from "@/components/ui/card";

export default function Home() {
	return (
		<div className="py-[2rem] grid gap-[1rem] md:grid-cols-2 px-[1rem] md:px-[3rem] lg:px-[12rem]">
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
			<SubtopicCard />
		</div>
	);
}

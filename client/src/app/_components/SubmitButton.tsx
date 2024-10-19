import { Loader2 } from "lucide-react";
import { Button } from "@/components/ui/button";

export function SubmitButton({ type, variant, displayName, isProcessing, onSubmit }: { type: "submit" | "button", variant?: "destructive", displayName: string, isProcessing: boolean, onSubmit: Function }) {
    return (
        <Button type={type} variant={variant || "default"} onClick={() => { onSubmit() }} disabled={isProcessing}>
            {
                isProcessing ? (
                    <Loader2 className="animate-spin" />
                ) : (
                    <span>{displayName}</span>
                )
            }
        </Button>
    );
}
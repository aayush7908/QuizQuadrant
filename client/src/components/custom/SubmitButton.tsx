import { Loader2 } from "lucide-react";
import { Button } from "../ui/button";

export function SubmitButton({ isProcessing, onSubmit }: { isProcessing: boolean, onSubmit: Function }) {
    return (
        <Button type="submit" onClick={() => { onSubmit() }} disabled={isProcessing}>
            {
                isProcessing ? (
                    <Loader2 className="animate-spin" />
                ) : (
                    <span>Verify</span>
                )
            }
        </Button>
    );
}
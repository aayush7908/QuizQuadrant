import React, {useState} from "react";
import "./styles.css"
import SignInForm from "./Signin";
import SignUpForm from "./Signup";

export default function Sign() {
    const [type, setType] = useState("signIn");
    const handleOnClick = text => {
        if (text !== type) {
            setType(text);
            return;
        }
    };
    const containerClass =
        "container " + (type === "signUp" ? "right-panel-active" : "");
    return (
        <div className="Sign h-full">
            <div className={`${containerClass}`} id="container">
                <SignUpForm screenSize={window.screen.width >= 1280 ? "" : "mobile"} type={type} handleOnClick={handleOnClick} />
                <SignInForm screenSize={window.screen.width >= 1280 ? "" : "mobile"} type={type} handleOnClick={handleOnClick} />
                <div className="overlay-container hidden xl:block lg:block">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1>Welcome Back!</h1>
                            <p>
                                To keep connected with us please login with your personal info
                            </p>
                            <button
                                className="ghost"
                                id="signIn"
                                onClick={() => handleOnClick("signIn")}
                            >
                                Sign In
                            </button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1>Hello, User !</h1>
                            <p>Enter your personal details and start journey with us</p>
                            <button
                                className="ghost "
                                id="signUp"
                                onClick={() => handleOnClick("signUp")}
                            >
                                Sign Up
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

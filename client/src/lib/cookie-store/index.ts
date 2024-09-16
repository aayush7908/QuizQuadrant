import Cookies from "js-cookie";

const TOKEN_NAME = "authToken";
const PASSWORD_RESET_TOKEN_NAME = "passwordResetToken";

const getToken = () => {
    return Cookies.get(TOKEN_NAME);
}

const setToken = (token: string) => {
    Cookies.set(TOKEN_NAME, token, { expires: 7 });
}

const removeToken = () => {
    Cookies.remove(TOKEN_NAME);
}

const getPasswordResetToken = () => {
    return Cookies.get(PASSWORD_RESET_TOKEN_NAME);
}

const setPasswordResetToken = (token: string) => {
    Cookies.set(PASSWORD_RESET_TOKEN_NAME, token);
}

const removePasswordResetToken = () => {
    Cookies.remove(PASSWORD_RESET_TOKEN_NAME);
}

export {
    getToken,
    setToken,
    removeToken,
    getPasswordResetToken,
    setPasswordResetToken,
    removePasswordResetToken
}
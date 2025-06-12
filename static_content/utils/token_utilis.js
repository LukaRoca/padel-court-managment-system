
let token = "46fc162a-309d-4609-8307-3bf81bb55b52"

/*
export function getToken() {
    return token
}

export function setToken(setoken) {
    token = setoken
}

 */

export function getToken() {
    const userData = JSON.parse(sessionStorage.getItem('user') || '{}');
    return userData.token || null;
}


export function setToken(newToken) {
    const userData = JSON.parse(sessionStorage.getItem('user') || '{}');
    userData.token = newToken;
    sessionStorage.setItem('user', JSON.stringify(userData));
}

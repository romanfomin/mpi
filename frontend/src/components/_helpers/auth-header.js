import { authenticationService } from '../_services';

export function authHeader() {
    // return authorization header with jwt token
    const currentUser = authenticationService.currentUserValue;
    if (currentUser && currentUser.accessToken) {
        return { Authorization: `${currentUser.tokenType} ${currentUser.accessToken}` };
    } else {
        return {};
    }
}

export function addAuthHeader(requestOptions){
    requestOptions.headers = Object.assign({}, requestOptions.headers, authHeader());
    console.log('scripts:' )
    console.log(requestOptions)
    return requestOptions
}
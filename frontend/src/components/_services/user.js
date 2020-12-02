
import { handleResponse, addAuthHeader } from "../_helpers";
const usersUrl = `${process.env.REACT_APP_API_URL}/api/users`;

export const userService = {
  getAll,
  create, 
  update,
};

function getAll() {
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" }
  };
  return fetch(usersUrl, addAuthHeader(requestOptions)).then(handleResponse);
}



function create(airDate, text) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ airDate, text })
  };
  return fetch(usersUrl, addAuthHeader(requestOptions)).then(handleResponse);
}

function update(id, roles){
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(roles)
  };
  return fetch(usersUrl + '/' + id + '/updateroles/', addAuthHeader(requestOptions)).then(handleResponse);
}
import config from "config";
import { handleResponse, addAuthHeader } from "@/_helpers";
const usersUrl = `${config.apiUrl}/api/users`;

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
    body: JSON.stringify({ id, roles })
  };
  return fetch(usersUrl + '/' + id, addAuthHeader(requestOptions)).then(handleResponse);
}
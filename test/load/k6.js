import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
  vus: 100,
  duration: '10s',
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95 percent of response times must be below 500ms
  },
};

const SLEEP_DURATION = 0.1;

export function setup() {
  const url = 'https://oauth2.crossnetcorp.com/auth/realms/microservicios/protocol/openid-connect/token';
  const params = {
    'Content-Type': 'application/x-www-form-urlencoded',
  };
  const requestBody = {
    client_id: 'partyreferencedata',
    username: 'SD Party Reference Data',
    password: 'CHANGEME',
    grant_type: 'password',
    client_secret: 'c0729700-818f-434c-93a9-1ff30939baf0',
  };
  const response = http.post(url, requestBody, params);
  return response.json();
}

export default function(data) {
  const url = 'https://api.crossnetcorp.com/partyreferencedata/api/v1/partyreferencedata?limit=30&offset=0';
  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${data.access_token}`
    },
  };
  http.get(url, params);
  sleep(SLEEP_DURATION);
}

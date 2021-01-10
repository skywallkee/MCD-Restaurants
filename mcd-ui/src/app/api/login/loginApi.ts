import { Injectable } from '@angular/core';
import ajax from 'superagent';
import config from '../../config/index';

@Injectable({
    providedIn: 'root'
})
export class LoginServiceApi {
    async login(username: string, password: string): Promise<string> {
        const resp = await ajax
            .post(config.endpoint.login)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json')
            .send({ username, password });
        return resp.body.key;
    }

    async register(username: string, password1: string, password2: string): Promise<any> {
        const resp = await ajax
            .post(config.endpoint.register)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json')
            .send({ username, password1, password2, email: "test@abc.com" });
        return resp;
    }
}

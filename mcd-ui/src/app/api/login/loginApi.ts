import { Injectable } from '@angular/core';
import ajax from 'superagent';
import config from '../../config/index';

@Injectable({
    providedIn: 'root'
})
export class LoginServiceApi {
    async login(username: string, password: string): Promise<any> {
        const resp = await ajax
            .post(config.endpoint.login)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json')
            .send({ username, password });
        return resp;
    }

    async register(username: string, password: string): Promise<any> {
        const resp = await ajax
            .post(config.endpoint.register)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json')
            .send({ username, password });
        return resp;
    }
}

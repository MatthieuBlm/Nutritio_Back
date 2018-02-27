import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Meal } from './meal.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MealService {

    private resourceUrl = SERVER_API_URL + 'api/meals';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(meal: Meal): Observable<Meal> {
        const copy = this.convert(meal);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(meal: Meal): Observable<Meal> {
        const copy = this.convert(meal);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Meal> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Meal.
     */
    private convertItemFromServer(json: any): Meal {
        const entity: Meal = Object.assign(new Meal(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Meal to a JSON which can be sent to the server.
     */
    private convert(meal: Meal): Meal {
        const copy: Meal = Object.assign({}, meal);

        copy.date = this.dateUtils.toDate(meal.date);
        return copy;
    }
}

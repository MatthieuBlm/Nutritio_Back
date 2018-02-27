import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Grocerie } from './grocerie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GrocerieService {

    private resourceUrl = SERVER_API_URL + 'api/groceries';

    constructor(private http: Http) { }

    create(grocerie: Grocerie): Observable<Grocerie> {
        const copy = this.convert(grocerie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(grocerie: Grocerie): Observable<Grocerie> {
        const copy = this.convert(grocerie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Grocerie> {
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
     * Convert a returned JSON object to Grocerie.
     */
    private convertItemFromServer(json: any): Grocerie {
        const entity: Grocerie = Object.assign(new Grocerie(), json);
        return entity;
    }

    /**
     * Convert a Grocerie to a JSON which can be sent to the server.
     */
    private convert(grocerie: Grocerie): Grocerie {
        const copy: Grocerie = Object.assign({}, grocerie);
        return copy;
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Grocerie } from './grocerie.model';
import { GrocerieService } from './grocerie.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-grocerie',
    templateUrl: './grocerie.component.html'
})
export class GrocerieComponent implements OnInit, OnDestroy {
groceries: Grocerie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private grocerieService: GrocerieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.grocerieService.query().subscribe(
            (res: ResponseWrapper) => {
                this.groceries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGroceries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Grocerie) {
        return item.id;
    }
    registerChangeInGroceries() {
        this.eventSubscriber = this.eventManager.subscribe('grocerieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

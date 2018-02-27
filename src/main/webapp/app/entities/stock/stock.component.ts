import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Stock } from './stock.model';
import { StockService } from './stock.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-stock',
    templateUrl: './stock.component.html'
})
export class StockComponent implements OnInit, OnDestroy {
stocks: Stock[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stockService: StockService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.stockService.query().subscribe(
            (res: ResponseWrapper) => {
                this.stocks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStocks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Stock) {
        return item.id;
    }
    registerChangeInStocks() {
        this.eventSubscriber = this.eventManager.subscribe('stockListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

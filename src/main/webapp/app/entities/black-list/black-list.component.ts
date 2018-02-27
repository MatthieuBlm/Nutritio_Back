import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BlackList } from './black-list.model';
import { BlackListService } from './black-list.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-black-list',
    templateUrl: './black-list.component.html'
})
export class BlackListComponent implements OnInit, OnDestroy {
blackLists: BlackList[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private blackListService: BlackListService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.blackListService.query().subscribe(
            (res: ResponseWrapper) => {
                this.blackLists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBlackLists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BlackList) {
        return item.id;
    }
    registerChangeInBlackLists() {
        this.eventSubscriber = this.eventManager.subscribe('blackListListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BlackList } from './black-list.model';
import { BlackListService } from './black-list.service';

@Component({
    selector: 'jhi-black-list-detail',
    templateUrl: './black-list-detail.component.html'
})
export class BlackListDetailComponent implements OnInit, OnDestroy {

    blackList: BlackList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private blackListService: BlackListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBlackLists();
    }

    load(id) {
        this.blackListService.find(id).subscribe((blackList) => {
            this.blackList = blackList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBlackLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'blackListListModification',
            (response) => this.load(this.blackList.id)
        );
    }
}

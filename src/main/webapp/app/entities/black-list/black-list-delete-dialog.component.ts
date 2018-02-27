import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BlackList } from './black-list.model';
import { BlackListPopupService } from './black-list-popup.service';
import { BlackListService } from './black-list.service';

@Component({
    selector: 'jhi-black-list-delete-dialog',
    templateUrl: './black-list-delete-dialog.component.html'
})
export class BlackListDeleteDialogComponent {

    blackList: BlackList;

    constructor(
        private blackListService: BlackListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.blackListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'blackListListModification',
                content: 'Deleted an blackList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-black-list-delete-popup',
    template: ''
})
export class BlackListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blackListPopupService: BlackListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.blackListPopupService
                .open(BlackListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { PersonDialogComponent } from '../../../../../../main/webapp/app/entities/person/person-dialog.component';
import { PersonService } from '../../../../../../main/webapp/app/entities/person/person.service';
import { Person } from '../../../../../../main/webapp/app/entities/person/person.model';
import { StockService } from '../../../../../../main/webapp/app/entities/stock';
import { GrocerieService } from '../../../../../../main/webapp/app/entities/grocerie';
import { BlackListService } from '../../../../../../main/webapp/app/entities/black-list';

describe('Component Tests', () => {

    describe('Person Management Dialog Component', () => {
        let comp: PersonDialogComponent;
        let fixture: ComponentFixture<PersonDialogComponent>;
        let service: PersonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [PersonDialogComponent],
                providers: [
                    StockService,
                    GrocerieService,
                    BlackListService,
                    PersonService
                ]
            })
            .overrideTemplate(PersonDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Person(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.person = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Person();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.person = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

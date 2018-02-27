/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { BlackListComponent } from '../../../../../../main/webapp/app/entities/black-list/black-list.component';
import { BlackListService } from '../../../../../../main/webapp/app/entities/black-list/black-list.service';
import { BlackList } from '../../../../../../main/webapp/app/entities/black-list/black-list.model';

describe('Component Tests', () => {

    describe('BlackList Management Component', () => {
        let comp: BlackListComponent;
        let fixture: ComponentFixture<BlackListComponent>;
        let service: BlackListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [BlackListComponent],
                providers: [
                    BlackListService
                ]
            })
            .overrideTemplate(BlackListComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlackListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlackListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BlackList(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.blackLists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
